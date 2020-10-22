import React from "react";
import './App.css';

const useInterval = (callback, delay) => {
    const savedCallback = React.useRef();

    React.useEffect(() => {
        savedCallback.current = callback;
    }, [callback]);

    React.useEffect(() => {
        function tick() {
            savedCallback.current();
        }

        if (delay !== null) {
            let id = setInterval(tick, delay);
            return () => clearInterval(id);
        }
    }, [delay]);
};

const App = () => {

    const [people, setPeople] = React.useState([])

    useInterval(() => {
        fetch('/api/people')
            .then(res => res.json())
            .then(data => {
                if(data !== people) {
                    console.log(data)
                    setPeople(data);
                }
            })
    }, 1000)

    const peopleList = () =>
        people.map((person, index) =>
            <li key={index}>{person.name}</li>)

    return (
        <div className="App">
            <p>Herro World!</p>
            {peopleList()}
        </div>
    );
}

export default App;
