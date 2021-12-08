import React, { forwardRef } from "react";

const Column = forwardRef((props, ref) => {
  return (
    <div ref={ref}>
      <div
        className="array-item"
        style={{
          height: `${props.item}px`,
          backgroundColor: props.colour,
        }}
      ></div>
      <div>{props.item}</div>
    </div>
  );
});

export default Column;
