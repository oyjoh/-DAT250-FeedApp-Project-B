import React from "react";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import makeStyles from "@material-ui/core/styles/makeStyles";
import axios from "axios";
import ButtonGroup from "@material-ui/core/ButtonGroup";

function getModalStyle() {
    const top = 50;
    const left = 50;

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const useStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        width: 400,
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(2, 4, 3),
    },
}));

function VoteButtonComponent(props) {

    const classes = useStyles();
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const sendVote = (vote) => {
        const data = JSON.stringify(
            {"value": vote, "number": 1}
        );

        const config = {
            method: 'post',
            url: '/api/polls/' + props.pollId + '/entry?personId=' + props.personId,
            headers: {
                'Authorization': 'Bearer ' + props.cookie,
                'Content-Type': 'application/json',
            },
            data: data
        };

        axios(config)
            .then((res) => {
                if (res.data === "") alert("You have already voted on this poll!");
                console.log(res);
            })
            .catch((error) => {
                alert("Error!\n" + error);
            })
    };

    const handleClick = (value) => {
        sendVote(value);
    }

    return (
        <div>
            <Button disableElevation variant="outlined" onClick={handleOpen}>Vote on Poll</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <div style={modalStyle} className={classes.paper}>
                    <h2 id="simple-modal-title" style={{textAlign: "-webkit-center"}}>{props.summary}</h2>
                    <div style={{textAlign: "-webkit-center"}}>
                        <p>Poll code: {props.pollCode}</p>
                        <ButtonGroup disableElevation variant="contained" fullWidth size="large">
                            <Button onClick={() => {
                                handleClick("YES");
                                handleClose();
                                setTimeout(() => {
                                    props.action(props.pollCode)
                                }, 500)
                            }} color="primary">YES</Button>
                            <Button onClick={() => {
                                handleClick("NO");
                                handleClose();
                                setTimeout(() => {
                                    props.action(props.pollCode)
                                }, 500)
                            }} color="secondary">NO</Button>
                        </ButtonGroup>
                    </div>
                </div>
            </Modal>
        </div>
    );
}

export default VoteButtonComponent;
