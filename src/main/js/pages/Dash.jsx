import Container from "@material-ui/core/Container";
import React from "react";
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

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

function createData(pollcode, yes, no, distribution, polldescription, enddate) {
    return { pollcode, yes, no, distribution, polldescription, enddate };
}

const rows = [
    createData('351 136', 55, 6, "88.2%", "Do you want Pizza?", "25/04/21"),
    createData('121 612', 32, 9, "74,0%","Can chickens fly?" , "25/12/20"),
    createData('125 512', 43, 16,"93,4%", "Yes or no?", "12/05/21"),
    createData('215 703', 3, 3,"50%" , "Are you coming to the match?", "01/01/21"),
    createData('854 153', 11, 10, "52.5%", "Do you like The Beach Boys?", "06/06/21"),
];

function Dash() {
    const classes = useStyles();

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
                            {rows.map((row) => (
                                <TableRow key={row.pollcode}>
                                    <TableCell component="th" scope="row">
                                        {row.pollcode}
                                    </TableCell>
                                    <TableCell align="right">{row.yes}</TableCell>
                                    <TableCell align="right">{row.no}</TableCell>
                                    <TableCell align="right">{row.distribution}</TableCell>
                                    <TableCell align="right">{row.polldescription}</TableCell>
                                    <TableCell align="right">{row.enddate}</TableCell>
                                    <TableCell align="right">
                                        <VoteButtonComponent pollcode={row.pollcode}/>
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
