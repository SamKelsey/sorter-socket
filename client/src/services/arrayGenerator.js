export const createUniqueArray = () => {
  let arr = [2, 2];
  while (isDuplicates(arr)) {
    arr = Array.from({ length: 10 }, () => Math.floor(Math.random() * 100));
  }

  return arr;
};

/* 
    Hacky method to ensure an array doesn't include
    any duplicates :(
*/
const isDuplicates = (arr) => {
  for (let i = 0; i < arr.length; i++) {
    const result = arr.filter((num) => num === arr[i]);

    if (result.length > 1) {
      return true;
    }
  }
  return false;
};
