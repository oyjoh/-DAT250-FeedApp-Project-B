import React, {useState} from "react";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import makeStyles from "@material-ui/core/styles/makeStyles";
import axios from "axios";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import TextField from "@material-ui/core/TextField";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import Switch from "@material-ui/core/Switch";

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
    textField:{
        padding: theme.spacing(1),
    },
}));

function PollCreatorComponent(props) {

    const classes = useStyles();
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const [form, setForm] = useState({
        "summary": "",
        "isPublic": true,
        "endAt": "",
    });

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSummaryChange = (event) => {
        const value = event.target.value;
        setForm({...form, summary: value});
    }

    const handleDateTimeChange = (event) => {
        const value = event.target.value;
        const date = new Date(value);
        console.log(date);
        setForm({...form, endAt: date.toISOString()});
    }

    const handleSwitchChange = (event) => {
        const value = event.target.value;
        console.log(value);
        setForm({...form, isPublic: !form.isPublic});
    }

    const handleSubmit = (event) => {
        console.log("SUBMITTED");
        const data = JSON.stringify(form);
        console.log(data);
        const config = {
            method: 'post',
            url: '/api/people/' + props.personId + '/polls/',
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
                    <form className={classes.form} onSubmit={handleSubmit} noValidate>
                        <TextField
                            className={classes.textField}
                            variant="outlined"
                            margin="normal"
                            required
                            onChange={handleSummaryChange}
                            fullWidth
                            id="summary"
                            label="Enter a short summary"
                            name="summary"
                            autoComplete="email"
                            autoFocus
                        />
                        <br/>
                        <TextField
                            className={classes.textField}
                            onChange={handleDateTimeChange}
                            variant="outlined"
                            fullWidth
                            id="datetime-local"
                            name="date"
                            label="End time"
                            type="datetime-local"
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <br/>
                        <FormControlLabel
                            control={<Switch
                                checked={form.isPublic}
                                color="primary"
                                onChange={handleSwitchChange}
                                name="isPublic"
                            />}
                            label="Public"
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                        >
                            Submit
                        </Button>
                    </form>
                </div>
            </Modal>
        </div>
    );
}
export default PollCreatorComponent;
