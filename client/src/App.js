import React, { useState, useEffect } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

import "./app.css";

export const App = () => {
  const [stompClient, setStompClient] = useState("");
  const [array, setArray] = useState([]);
  const [sortingSpeed, setSortingSpeed] = useState(5);

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

    // Envoked like ComponentWillUnmount - Disconnects from socket.
    // return function cleanup() {
    //   stompClient.disconnect(() => {
    //     console.log("Disconnected");
    //   });
    // };
  }, []);

  const sendMessage = () => {
    stompClient.send(
      "/app/sort",
      {},
      JSON.stringify({
        "sorting-list": array,
        "sorting-method": "Quicksort",
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
