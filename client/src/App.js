import React, { useState, useEffect } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import FlipMove from "react-flip-move";
import Column from "./Column";

import "./app.css";

export const App = () => {
  const [stompClient, setStompClient] = useState("");
  const [array, setArray] = useState([]);
  const [evaluatedIndexes, setEvaluatedIndexes] = useState([]);
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
          body: { "sorting-list": sortingList, "evaluated-indexes": indexes },
        } = JSON.parse(body);
        sortingList && setArray(sortingList);
        indexes && setEvaluatedIndexes(indexes);
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
    let arr = [2, 2];
    while (doubleValue(arr)) {
      arr = Array.from({ length: 10 }, () => Math.floor(Math.random() * 100));
    }
    setEvaluatedIndexes([]);
    setArray(arr);
  };

  /* 
  Disgusting/hacky method to ensure the array doesn't include
  any duplicates, as duplicates cause a ui bug... :(
  */
  const doubleValue = (arr) => {
    for (let i = 0; i < arr.length; i++) {
      const result = arr.filter((num) => num == arr[i]);

      if (result.length > 1) {
        return true;
      }
    }
    return false;
  };

  const getColour = (index) => {
    if (evaluatedIndexes.includes(index)) {
      return "blue";
    }

    return "green";
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
      <FlipMove className="array-wrapper">
        {array.map((item, index) => (
          <Column key={item} item={item} colour={getColour(index)} />
        ))}
      </FlipMove>
    </div>
  );
};

export default App;
