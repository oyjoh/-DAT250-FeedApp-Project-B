import React, {useEffect} from "react";
import './App.css';


const App = () => {

    const [state, setState] = React.useState({
        people: [],
    })

    useEffect(() => {
        fetch('/people')
            .then(res => res.json())
            .then(data => {
                console.log(data)
                setState({people: data})
            })
    })

    const peopleList = () => {
        state.people.map(elem => <li>elem.name</li>)

    }
    return (
        <div className="App">
            <p>Hello World!</p>
            {peopleList}
        </div>
    );
}

export default App;
