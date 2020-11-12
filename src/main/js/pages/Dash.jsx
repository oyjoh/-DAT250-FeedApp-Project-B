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
import axios from "axios";
import "core-js/stable";
import "regenerator-runtime/runtime";
import Polltable from "../components/Polltable.jsx";
import Pollsearch from "../components/Pollsearch.jsx";

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

const Dash = () => {

    const [person, setPerson] = useState({});
    const [loading, setLoading] = useState(false);

    const getPerson = async (cookie) => {
        const config = {
            method: 'get',
            url: '/api/people/current',
            headers: {
                Authorization: 'Bearer ' + cookie,
            }
        };

        return axios(config)
            .then(res => {
                console.log(res.data);
                return res.data;
            });
    }

    const cookieValue = (cookieName) => document.cookie
        .split('; ')
        .find(row => row.startsWith(cookieName))
        .split('=')[1];

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const cookie = cookieValue("Authorization");
            console.log(cookie);
            const result = await getPerson(cookie);
            console.log(result);
            setPerson(result);
            setLoading(false);
        };
        fetchData();
    }, []);

    const classes = useStyles();

    return (
        <div>
        <AppBar position="static">
            <Toolbar>
                <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    User: {person.name}
                </Typography>
            </Toolbar>
        </AppBar>
            <Container style={{paddingTop: "7em"}}>
                <Pollsearch/>
                <Polltable {...{polls: person.polls, cookie: person.cookie}}/>
            </Container>
        </div>
    );
}
export default Dash;
