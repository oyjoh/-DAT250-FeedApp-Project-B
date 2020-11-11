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
import Polltable from "../components/Polltable.jsx"

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

const Dash = () => {

    const person = {
        "createdAt": "2020-11-11T16:43:37.871+00:00",
        "updatedAt": "2020-11-11T16:43:37.980+00:00",
        "personId": 98,
        "name": "ola1605113017326",
        "email": "ola1605113017326@gmail.com",
        "cookie": "bce716d4-a4a7-458a-9273-60b65c138e66",
        "roles": [
            "USER"
        ],
        "polls": [
            104,
            105,
            106,
            107
        ],
        "entries": [
            118,
            119,
            120,
            121,
            122,
            123,
            124,
            125,
            126,
            127
        ],
        "enabled": true,
        "username": "98",
        "admin": false,
        "user": true
    }

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
                <Polltable {...{polls: person.polls, cookie: person.cookie}}/>
            </Container>
        </div>
    );
}
export default Dash;
