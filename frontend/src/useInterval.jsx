import { useEffect } from 'react';


// useInterval  훅 (setInterval 대체)
export const useInterval = (callback, delay) => {
  useEffect(() => {
    const intervalId = setInterval(callback, delay);
    return () => clearInterval(intervalId);
  }, [callback, delay]);
};