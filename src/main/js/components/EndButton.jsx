import React, {useState} from "react";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        width: 400,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
    textField:{
        padding: theme.spacing(1),
    },
}));

const EndButton = (props) => {


    const handleRestart = () => {
        handleSubmit(null, false);
    }

    const handleEnd = () => {
        const date = new Date();
        handleSubmit(date.toISOString(), true);
    }

    const handleSubmit = (newEndAt, ended) => {
        console.log("SUBMITTED");
        const data = JSON.stringify({
            "endAt": newEndAt,
            "ended": false,
        });
        console.log(data);
        const config = {
            method: 'put',
            url: '/api/people/' + props.personId + '/polls/' + props.poll.pollId,
            headers: {
                'Content-Type' : 'application/json',
                Authorization: 'Bearer ' + props.cookie,
            },
            data: data
        };
        axios(config)
            .then((res) => {
                console.log(res);
            })
            .catch((error) => {
                alert(error)
            });
    };

    const btn = props.poll.ended ?
        <Button color="primary" disableElevation variant="contained" onClick={handleRestart}>RESTART</Button> :
        <Button color="secondary" disableElevation variant="contained" onClick={handleEnd}>END</Button>;

    return (
        <div>
            {btn}
        </div>
    );
}

export default EndButton;
