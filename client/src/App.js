import React, { useState, useEffect } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

import "./app.css";

export const App = () => {
  const [stompClient, setStompClient] = useState("");
  const [array, setArray] = useState([]);
  const [sortingSpeed, setSortingSpeed] = useState(5);
  const [sortingMethods, setSortingMethods] = useState([]);
  const [sortingMethod, setSortingMethod] = useState("Bubblesort");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Connect to socket
    var socket = new SockJS("http://localhost:8080/socket");
    const stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
      stomp.subscribe("/sorting", function ({ body }) {
        const {
          body: { "sorting-list": sortingList },
        } = JSON.parse(body);

        sortingList && setArray(sortingList);
      });
      stomp.subscribe("/errors", function (response) {});
    });
    setStompClient(stomp);

    fetchData();

    // Envoked like ComponentWillUnmount - Disconnects from socket.
    // return function cleanup() {
    //   stompClient.disconnect(() => {
    //     console.log("Disconnected");
    //   });
    // };
  }, []);

  async function fetchData() {
    const res = await fetch("http://localhost:8080/sorter-methods");
    const data = await res.json();
    setSortingMethods(data["sorter-methods"]);
    setLoading(false);
  }

  const sendMessage = () => {
    stompClient.send(
      "/app/sort",
      {},
      JSON.stringify({
        "sorting-list": array,
        "sorting-method": sortingMethod,
        "sorting-speed": sortingSpeed,
      })
    );
  };

  const generateArray = () => {
    setArray(Array.from({ length: 10 }, () => Math.floor(Math.random() * 100)));
  };

  return (
    <div>
      <button onClick={() => sendMessage()}>Sort</button>
      <button onClick={() => generateArray()}>Generate</button>
      <input
        type="range"
        min="1"
        max="10"
        value={sortingSpeed}
        onChange={(e) => setSortingSpeed(e.target.value)}
      />
      <label>{sortingSpeed}</label>
      <select onChange={(e) => setSortingMethod(e.target.value)}>
        <option value="">Select a sorting method...</option>
        {!loading &&
          sortingMethods.map((method) => (
            <option key={method} value={method}>
              {method}
            </option>
          ))}
      </select>
      <div className="array-wrapper">
        {/* I think this is where the ui bug is occuring. */}
        {array.map((item, index) => (
          <div>
            <div
              className="array-item"
              key={index}
              style={{ height: `${item}px` }}
            ></div>
            <div>{item}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default App;
