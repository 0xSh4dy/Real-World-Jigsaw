const express = require('express');
const mongoose = require('mongoose');
const app = express();
const cors = require('cors');
const bcrypt = require('bcrypt');
const crypto = require('crypto-js');
const nodemailer = require('nodemailer');
const saltRounds = 10;
require('dotenv').config();
var user = "";
var email1 = "";
app.use(express.json());
app.use(cors());
app.use(express.urlencoded({ extended: true }));
app.use(express.static("static"));
const connection_url = 'mongodb+srv://admin:BoAoAuyCcujHi0Ln@cluster0.qni0g.mongodb.net/NarutoDB?retryWrites=true&w=majority';
mongoose.connect(connection_url, { useNewUrlParser: true, useUnifiedTopology: true, useCreateIndex: true });
const JigsawSchema = mongoose.Schema({
    name: String,
    password: String,
    email: String,
    highScore: Number
})
const JigsawUsers = mongoose.model("jigsawUser", JigsawSchema);

// Email validation using Regex
function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


// Register handler
app.post("/register", (req, res) => {
    const username = req.body.username;
    const password = req.body.password;
    const email = req.body.email;
    const valid = validateEmail(email);
    if (valid) {
        JigsawUsers.findOne({ name: username }, (err, data) => {
            if (data === null) {
                JigsawUsers.findOne({ email: email }, (err1, data1) => {
                    if (data1 === null) {
                        bcrypt.hash(password, saltRounds, (error, hash) => {
                            if (error) { res.send("There was an error, please try again") }
                            else {
                                const newUser = new JigsawUsers({
                                    name: username,
                                    password: hash,
                                    email: email,
                                    highScore: 0
                                })
                                newUser.save((myerr, mydat) => {
                                    if (myerr) { res.send("There was an error, please try again") }
                                    else { res.send("Successfully registered") }
                                })
                            }
                        })
                    }
                    else if (data1 != null) {
                        res.send("Account with the given email already exists");
                    }
                })
            }
            else if (data != null) {
                res.send("Username is already taken");
            }
        })
    }
    else {
        res.send("Invalid");
    }

})
app.get("/", (req, res) => {
    res.sendFile(__dirname + "/index.html");
})

// Login Handler
app.post("/login", (req, res) => {
    const username = req.body.username;
    const password = req.body.password;
    const email = req.body.email;
    JigsawUsers.findOne({ email: email }, (err, data) => {
        if (data === null) {
            res.send("No account exists with that email");
        }
        else if (data !== null) {
            if (username != data.name) {
                res.send("Invalid username");
            }
            else if (username === data.name) {
                bcrypt.compare(password, data.password, (error, result) => {
                    if (result === true) {
                        user = username;
                        res.send("Success");
                    }
                    else {
                        res.send("Invalid password");
                    }
                })
            }
        }
    })
})
app.get("/login", (req, res) => {
    res.send(user);
})
app.post("/scoreboard", (req, res) => {
    var score = req.body.score;
    var scr;
    score = parseFloat(score);
    JigsawUsers.findOne({ name: req.body.username }, (err, data) => {
        if (data == null) {
            res.send("Error");
        }
        else {
            scr = data.highScore;
            if (score > scr) {
                JigsawUsers.updateOne({ name: req.body.username }, {
                    $set: {
                        highScore: score
                    }
                }, (error, data) => {
                    if (error) {
                        res.send("Error");
                    }
                    else {
                        res.send("Updated");
                    }
                })
            }
        }
    })

});

// The scoreboard
app.get("/scoreboard", (req, res) => {
    let dataToSend = [];
    JigsawUsers.find().sort('-highScore').exec((err, data) => {
        if (err) {
            res.send("Error");
        }
        else {
            const len = data.length;
            for (let i = 0; i < len; i++) {
                dataToSend.push({
                    username: data[i].name,
                    highScore: data[i].highScore
                })
            }
            res.send(dataToSend);
        }
    })

})
var hash = "hash";
// Reset login details by using email
app.post("/reset/em", (req, res) => {
    let num = Math.floor(100 * Math.random());
    num = String(num);
    let em = String(req.body.email);
    email1 = req.body.email;
    JigsawUsers.findOne({ email: email1 }, (myerr, mydat) => {
        if (myerr) {
            res.send("No");
        }
        else {
            if (mydat != null) {
                em += num;
                hash = crypto.MD5(em);
                url = `https://jigsaw-real.herokuapp.com/reset/${hash}`;
                hash = `${hash}`

                // let email = data.email;
                const transporter = nodemailer.createTransport({
                    service: 'gmail',
                    auth: {
                        user: process.env.EMAIL,
                        pass: process.env.PASSWORD
                    }

                });
                const mailOptions = {
                    from: process.env.EMAIL,
                    to: email1,
                    subject: 'Password/Username Reset',
                    html: `<div><h2>The password reset link is </h2><a href=${url}>${url}</a><h2>Ignore this email if you didn't request for changing your password fot the Real World Jigsaw game</h2></div>`
                };
                transporter.sendMail(mailOptions, (error, info) => {
                    if (error) {
                        res.send("Error");
                    }
                    else {
                        res.send("Done");
                    }
                })
                app.get(`/reset/${hash}`, (req, res) => {
                    res.sendFile(__dirname + "/reset.html")
                })
            }
            else {
                res.send("Invalid");
            }
        }
    })

})
app.post("/reset", (req, res) => {
    let username = req.body.username;
    let password = req.body.password;
    if (username.length == 0 && password.length == 0) {
        res.send("No");
    }
    else if (username.length == 0 && password.length != 0) {
        bcrypt.hash(password, saltRounds, (error, hash) => {
            JigsawUsers.findOne({ email: email1 }, (err, data) => {
                if (err) {
                    res.send("No");
                }
                else {
                    if (data == null) {
                        res.send("Invalid");
                    }
                    else {
                        JigsawUsers.updateOne({ email: email1 }, {
                            $set: {
                                password: hash
                            }
                        }, (error1, data1) => {
                            if (error1) {
                                res.send("No");
                            }
                            else {
                                res.send("Updated");
                            }
                        })
                    }
                }
            })
        })

    }
    else if (username.length != 0 && password.length == 0) {
        JigsawUsers.findOne({ email: email1 }, (err2, data2) => {
            if (err2) {
                res.send("No");
            }
            else {
                if (data2 == null) {
                    res.send("Invalid");
                }
                else {
                    JigsawUsers.updateOne({ email: email1 }, {
                        $set: {
                            name: username
                        }
                    }, (err3, data3) => {
                        if (err3) {
                            res.send("No");
                        }
                        else {
                            res.redirect("/reset/redirect");
                        }
                    })
                }
            }
        })
    }
    else if (username.length != 0 && password.length != 0) {
        bcrypt.hash(password, saltRounds, (err4, hash) => {
            JigsawUsers.findOne({ email: email1 }, (err5, data5) => {
                if (err5) {
                    res.send("No");
                }
                else {
                    if (data5 == null) {
                        res.send("Invalid");
                    }
                    else {
                        JigsawUsers.updateOne({ email: email1 }, {
                            $set: {
                                name: username,
                                password: hash
                            }
                        }, (err6, data6) => {
                            if (err6) {
                                res.send("No");
                            }
                            else {
                                res.send("Updated");
                            }
                        })
                    }
                }
            })
        })
    }
})
app.get("/reset/redirect", (req, res) => {
    res.sendFile(__dirname + "/changed.html");
})

app.listen(process.env.PORT || 3500);