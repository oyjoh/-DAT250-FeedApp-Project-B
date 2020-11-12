import React, {useState} from "react";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import makeStyles from "@material-ui/core/styles/makeStyles";
import axios from "axios";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import TextField from "@material-ui/core/TextField";

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
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
}));

function PollCreatorComponent(props) {

    const classes = useStyles();
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const [form, setForm] = useState({
        "summary": "",
        "isPublic": true
    });

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = (event) => {
        console.log("SUBMITTED");
        const config = {
            method: 'post',
            url: '/api/people/' + props.personId + '/polls/',
            headers: {
                'Content-Type' : 'application/json',
                Authorization: 'Bearer ' + props.cookie,
            },
            data: JSON.stringify({
                "summary": form.summary,
                "isPublic": true,
            })
        };
        axios(config)
            .then((res) => {
                console.log(res);
            })
            .catch((error) => {
                alert(error)
            });
    };

    const handleChange = (event) => {
        const target = event.target;
        const name = target.name;
        const value = target.value;
        console.log(value);

        setForm({
            ...form,
            [name]: value,
        });
    };

    return (
        <div>
            <Button color="primary"  disableElevation variant="contained" onClick={handleOpen}>ADD A POLL</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <div style={modalStyle} className={classes.paper}>
                    <form className={classes.form} onSubmit={handleSubmit} onChange={handleChange} noValidate>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="summary"
                            label="Enter a short summary"
                            name="summary"
                            autoComplete="email"
                            autoFocus
                        />
                    </form>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >
                        Submit
                    </Button>
                </div>
            </Modal>
        </div>
    );
}
export default PollCreatorComponent;
