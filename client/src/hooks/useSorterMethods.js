import { useState, useEffect } from "react";

export function useSorterMethods() {
  const [sortingMethods, setSortingMethods] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await fetch("http://localhost:8080/sorter-methods");
        const data = await res.json();
        setSortingMethods(data["sorter-methods"]);
        setLoading(false);
      } catch (err) {
        console.error(`There was an error: ${err}`);
      }
    };

    fetchData();
  });

  return [sortingMethods, loading];
}
