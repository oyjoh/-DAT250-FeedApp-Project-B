import React, {useEffect, useState} from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import "core-js/stable";
import "regenerator-runtime/runtime";
import axios from "axios";
import SearchBar from "material-ui-search-bar";
import VoteButtonComponent from "../components/VoteButtonComponent.jsx";

const useStyles = makeStyles((theme) => ({
    }));


const Pollsearch = (props) => {
    const classes = useStyles();

    const [searchVal, setSearchVal] = useState("");
    const [poll, setPoll] = useState({});

    const handleChange = (event) => {
        const target = event.target;
        const value = target.value;

        setSearchVal(value);
    }

    const handleSubmit = (value) => {
        axios({
            method: 'get',
            url: '/api/polls/joinkey/' + value,
            headers: {
                Authorization: 'Bearer ' + props.cookie,
            }
        })
            .then(res => {
                console.log(res.data);
                return res.data
            })
            .then(newPoll => {
                setPoll(newPoll);
            })
            .catch((error) => {
                return error;
            });
    }

    const checkIfEqual = (pollKey, currVal) => {
        if(pollKey === undefined) {
            return false;
        }
        return pollKey.toString() === currVal;
    }

    const votePoll = checkIfEqual(poll.joinKey, searchVal) ?
        (poll.ended ? <div/> : <VoteButtonComponent {...{pollCode: poll.joinKey, cookie: props.cookie, personId: props.personId, pollId: poll.pollId, summary: poll.summary}}/>) :
            <div/>;

    return (
        <div style={{padding:"20px"}}>
            <SearchBar
                value={searchVal}
                onChange={(newValue) => setSearchVal(newValue)}
                onRequestSearch={() => handleSubmit(searchVal)}
            />
            {votePoll}
        </div>
    );
}
export default Pollsearch;
