import React, {useEffect, useState} from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import "core-js/stable";
import "regenerator-runtime/runtime";
import InputBase from "@material-ui/core/InputBase";
import SearchIcon from '@material-ui/icons/Search';
import {fade, Paper} from "@material-ui/core";

import axios from "axios";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
        table: {
            minWidth: 650,
        },
        root: {
            flexGrow: 1,
        },
        menuButton: {
            marginRight: theme.spacing(2),
        },
        title: {
            flexGrow: 1,
            display: 'none',
            [theme.breakpoints.up('sm')]: {
                display: 'block',
            },
        },
        search: {
            position: 'relative',
            borderRadius: theme.shape.borderRadius,
            backgroundColor: fade(theme.palette.common.white, 0.15),
            '&:hover': {
                backgroundColor: fade(theme.palette.common.white, 0.25),
            },
            marginLeft: 0,
            width: '100%',
            [theme.breakpoints.up('sm')]: {
                marginLeft: theme.spacing(1),
                width: 'auto',
            },
        },
        searchIcon: {
            padding: theme.spacing(0, 2),
            height: '100%',
            position: 'absolute',
            pointerEvents: 'none',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
        },
        inputRoot: {
            color: 'inherit',
        },
        inputInput: {
            padding: theme.spacing(1, 1, 1, 0),
            // vertical padding + font size from searchIcon
            paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
            transition: theme.transitions.create('width'),
            width: '100%',
            [theme.breakpoints.up('sm')]: {
                width: '12ch',
                '&:focus': {
                    width: '20ch',
                },
            },
        },
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

    const handleSubmit = (event) => {
        axios({
            method: 'get',
            url: '/api/polls/joinkey/' + searchVal,
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
        <div>
            <div className={classes.search}>
            <div className={classes.searchIcon}>
                <SearchIcon />
            </div>
                <InputBase
                    onChange={handleChange}
                    placeholder="Search for poll…"
                    classes={{
                        root: classes.inputRoot,
                        input: classes.inputInput,
                    }}
                    inputProps={{ 'aria-label': 'search' }}
                />

            </div>
            <Button
                onClick={handleSubmit}
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
            >
                Find poll
            </Button>
        </div>
    );
}
export default Polltable;
