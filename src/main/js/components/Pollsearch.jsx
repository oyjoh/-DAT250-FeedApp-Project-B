import Container from "@material-ui/core/Container";
import React, {useEffect, useState} from "react";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Paper from "@material-ui/core/Paper";
import makeStyles from "@material-ui/core/styles/makeStyles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import VoteButtonComponent from "../components/VoteButtonComponent.jsx";
import axios from "axios";
import "core-js/stable";
import "regenerator-runtime/runtime";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

const Polltable = (props) => {

    const classes = useStyles();

    const [pollList, setPollList] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const getPoll = async (pollId) => {
        const config = {
            method: 'get',
            url: '/api/polls/' + pollId,
            headers: {
                Authorization: 'Bearer ' + props.cookie,
            }
        };

        return axios(config)
            .then(res => {
                console.log(res.data);
                return res.data;
            });
    }

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            let newPollList = [];
            props.polls.forEach(pollId => {
                newPollList = newPollList.concat(getPoll(pollId));
            })

            const result = await Promise.all(newPollList);
            console.log(result);
            setPollList(result);
            setIsLoading(false);
        };
        console.log(pollList);
        fetchData();
    },[]);

    const distrbution = (yes, no) => {
        if(yes === no) return "50%";
        return (yes/(no+yes)/100).toFixed(2) + "%";
    }

    return (
        <Button variant="contained">Search</Button>
    );
}
export default Polltable;
