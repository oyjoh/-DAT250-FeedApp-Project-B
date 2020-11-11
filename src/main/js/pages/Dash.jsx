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

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

function createData(pollcode, yes, no, distribution, polldescription, enddate) {
    return { pollcode, yes, no, distribution, polldescription, enddate };
}

const Dash = () => {
    const rows = [
        createData('351 136', 55, 6, "88.2%", "Do you want Pizza?", "25/04/21"),
        createData('121 612', 32, 9, "74,0%","Can chickens fly?" , "25/12/20"),
        createData('125 512', 43, 16,"93,4%", "Yes or no?", "12/05/21"),
        createData('215 703', 3, 3,"50%" , "Are you coming to the match?", "01/01/21"),
        createData('854 153', 11, 10, "52.5%", "Do you like The Beach Boys?", "06/06/21"),
    ];

    const person = {
        createdAt: "2020-11-11T10:15:48.535+00:00",
        updatedAt: "2020-11-11T10:15:48.647+00:00",
        personId: 60,
        name: "ola1605089748209",
        email: "ola1605089748209@gmail.com",
        cookie: "26d885a2-85e7-45e6-ae4d-d52b7c44cf40",
        roles: [
            "USER"
        ],
        polls: [
            79,
            80,
            81
        ],
        entries: [],
        enabled: true,
        admin: false,
        username: "60",
        user: true
    }

    const classes = useStyles();

    const [pollList, setPollList] = useState([]);


    const getPoll = (pollId) => {
        const config = {
            method: 'get',
            url: '/api/polls/' + pollId,
            headers: {
                Authorization: 'Bearer ' + person.cookie,
            }
        };

        return axios(config)
            .then(res => { console.log(res.data); return res.data;});
    }

    useEffect(() => {
        let newPollList = []
        person.polls.forEach(pollId => {
            newPollList = newPollList.concat(getPoll(pollId));
        })
        setPollList(newPollList);
    },[]);


    return (
        <div>
        <AppBar position="static">
            <Toolbar>
                <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    User: Test_user
                </Typography>
            </Toolbar>
        </AppBar>
            <Container style={{paddingTop: "7em"}}>

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
                            {pollList.map((poll, index) => (
                                <TableRow key={index}>
                                    <TableCell component="th" scope="row">
                                        {poll.joinKey}
                                    </TableCell>
                                    <TableCell align="right">{10}</TableCell>
                                    <TableCell align="right">{7}</TableCell>
                                    <TableCell align="right">{4}</TableCell>
                                    <TableCell align="right">{poll.summary}</TableCell>
                                    <TableCell align="right">{poll.endAt}</TableCell>
                                    <TableCell align="right">
                                        <VoteButtonComponent pollcode={poll.joinKey}/>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Container>
        </div>
    );
}
export default Dash;
