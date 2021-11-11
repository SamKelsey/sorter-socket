import React, { useState, useEffect } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

import "./app.css";

export const App = () => {
  const [stompClient, setStompClient] = useState("");
  const [array, setArray] = useState([]);

  useEffect(() => {
    // Connect to socket
    var socket = new SockJS("http://localhost:8080/socket");
    const stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
      stomp.subscribe("/sorting", function (response) {
        setArray(JSON.parse(response.body));
      });
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
        "sorting-method": "Quicksort",
        "sorting-speed": 1,
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
      <div className="array-wrapper">
        {array.map((item) => (
          <div
            className="array-item"
            key={item}
            style={{ height: `${item}px` }}
          ></div>
        ))}
      </div>
      <div>{array}</div>
    </div>
  );
};

export default App;
