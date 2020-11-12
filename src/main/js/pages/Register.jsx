import '../App.css';
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import Box from "@material-ui/core/Box";
import Checkbox from "@material-ui/core/Checkbox";
import makeStyles from "@material-ui/core/styles/makeStyles";
import React, {useState} from "react";
import axios from "axios";
import {Redirect} from "react-router-dom";


function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://github.com/oyjoh/DAT250-FeedApp-Project-B">
                Group4
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

function Register() {
    const [login, setLogin] = useState(false);

    const classes = useStyles();

    const [form, setForm] = useState({
        "username": "testPerson1",
        "email": "",
        "password": "",
    });

    const handleChange = (event) => {
        const target = event.target;
        const name = target.name;
        const value = target.value;

        setForm({
            ...form,
            [name]: value
        });
    }

    const handleSubmit = (event) => {
        console.log(form);
        event.preventDefault();

        const data = JSON.stringify({
            "name": form.username,
            "email": form.email,
            "hash": form.password,
        });

        const config = {
            method: 'post',
            url: 'api/public/register',
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        };

        axios(config)
            .then(res => {
                console.log(res.data);
                setLogin(true);
                document.cookie = 'Authorization=' + res.data.cookie + ';max-age=604800;domain=localhost'
            })
            .catch((error) => {
                if (error.response.status === 401)
                    alert("Authorization Error!\nUsername or email already in use");
            });
    }

    if (login) {
        return <Redirect to="/dash"/>
    } else {
        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>

                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Register
                    </Typography>
                    <form className={classes.form} onSubmit={handleSubmit} onChange={handleChange} noValidate>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="username"
                            label="username"
                            name="username"
                            autoComplete="username"
                            autoFocus
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email Address"
                            name="email"
                            autoComplete="email"
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                        >
                            Sign Up
                        </Button>
                    </form>
                </div>
                <Box mt={8}>
                    <Copyright/>
                </Box>
            </Container>
        );
    }

}

export default Register;