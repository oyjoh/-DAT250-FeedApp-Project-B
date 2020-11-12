import React, {useEffect, useState} from "react";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Paper from "@material-ui/core/Paper";
import makeStyles from "@material-ui/core/styles/makeStyles";
import VoteButtonComponent from "../components/VoteButtonComponent.jsx";
import axios from "axios";
import "core-js/stable";
import "regenerator-runtime/runtime";
import withStyles from "@material-ui/core/styles/withStyles";
import {lighten} from "@material-ui/core";
import LinearProgress from "@material-ui/core/LinearProgress";

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

const Polltable = (props) => {

    const classes = useStyles();

    const BorderLinearProgress = withStyles({
        root: {
            height: 10,
            backgroundColor: '#ff6c5c',
        },
        bar: {
            backgroundColor: '#5c9eff',
        },
    })(LinearProgress);


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
        if(yes === no) return 50;
        return (yes/(no+yes)*100);
    }

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleString();
    }

    return (
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell><b>Poll Code</b></TableCell>
                                <TableCell align="right"><b>Yes</b></TableCell>
                                <TableCell align="right"><b>No</b></TableCell>
                                <TableCell align="right"><b>Distribution</b></TableCell>
                                <TableCell align="right"><b>Poll Description</b></TableCell>
                                <TableCell align="right"><b>End Date</b></TableCell>
                                <TableCell align="right"></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {pollList.map((poll) => (
                                <TableRow key={poll.pollId}>
                                    <TableCell component="th" scope="row">{poll.joinKey}</TableCell>
                                    <TableCell align="right">{poll.yes}</TableCell>
                                    <TableCell align="right">{poll.no}</TableCell>
                                    <TableCell align="right">
                                        <BorderLinearProgress
                                            className={classes.margin}
                                            variant="determinate"
                                            color="primary"
                                            value={distrbution(poll.yes, poll.no)}
                                        />
                                    </TableCell>
                                    <TableCell align="right">{poll.summary}</TableCell>
                                    <TableCell align="right">{formatDate(poll.endAt)}</TableCell>
                                    <TableCell align="right">
                                        <VoteButtonComponent {...{pollCode: poll.joinKey, cookie: props.cookie, personId: props.personId, pollId: poll.pollId, summary: poll.summary}}/>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
    );
}
export default Polltable;
