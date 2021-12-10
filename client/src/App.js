import React, { useState, useEffect } from "react";
import { useSorterMethods } from "./hooks/useSorterMethods";
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import FlipMove from "react-flip-move";
import Column from "./Column";
import { createUniqueArray } from "./services/arrayGenerator";

import "./app.css";

export const App = () => {
  const [stompClient, setStompClient] = useState("");
  const [array, setArray] = useState([]);
  const [evaluatedIndexes, setEvaluatedIndexes] = useState([]);
  const [sortingSpeed, setSortingSpeed] = useState(5);
  const [sortingMethod, setSortingMethod] = useState("Bubblesort");

  const [sortingMethods, loading] = useSorterMethods();

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
        "sorting-method": sortingMethod,
        "sorting-speed": sortingSpeed,
      })
    );
  };

  const generateArray = () => {
    const arr = createUniqueArray();
    setArray(arr);
    setEvaluatedIndexes([]);
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
          <Column
            key={item}
            item={item}
            colour={evaluatedIndexes.includes(index) ? "blue" : "green"}
          />
        ))}
      </FlipMove>
    </div>
  );
};

export default App;
