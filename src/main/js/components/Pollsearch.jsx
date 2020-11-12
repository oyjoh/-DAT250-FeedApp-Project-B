import React, {useEffect, useState} from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import "core-js/stable";
import "regenerator-runtime/runtime";
import {fade, Paper} from "@material-ui/core";
import axios from "axios";
import SearchBar from "material-ui-search-bar";

const useStyles = makeStyles((theme) => ({
    }));


const Polltable = (props) => {
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
                if (error.response.status === 401)
                    alert("Error");
            });
    }

    return (
        <div style={{padding:"20px"}}>
            <SearchBar
                value={searchVal}
                onChange={(newValue) => setSearchVal(newValue)}
                onRequestSearch={() => handleSubmit(searchVal)}
            />
        </div>
    );
}
export default Polltable;
