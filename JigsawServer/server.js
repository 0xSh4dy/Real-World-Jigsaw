const express = require('express');
const mongoose = require('mongoose');
const app = express();
const cors = require('cors');
const bcrypt = require('bcrypt');
const crypto = require('crypto-js');
const nodemailer = require('nodemailer');
const saltRounds = 10;
var userLog = "";
require('dotenv').config();
var user = "";
var email1 = "";
app.use(express.json());
app.use(cors());
app.set('view engine','ejs');
app.use(express.urlencoded({ extended: true }));
app.use(express.static("static"));
const connection_url = 'your_database_url';
mongoose.connect(connection_url, { useNewUrlParser: true, useUnifiedTopology: true, useCreateIndex: true });
const JigsawSchema = mongoose.Schema({
    name: String,
    password: String,
    email: String,
    highScore: Number
});
// This schema is for individual scores
const ScoreSchema = mongoose.Schema({
    name:String,
    scores:[{
        mode:String,
        score:String,
        nGame:Number
    }]
})
const JigsawUsers = mongoose.model("jigsawUser", JigsawSchema);
const JigsawScores = mongoose.model("jigsawScore",ScoreSchema);
// Email validation using Regex
function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


// Register handler
app.post("/register", (req, res) => {
    const username = String(req.body.username).trim();
    const password = String(req.body.password).trim();
    const email = String(req.body.email).trim();
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
                                const initiateScores = new JigsawScores({
                                    name:username,
                                    scores:{
                                        mode:"You have not played any game",
                                        score:0,
                                        nGame:0
                                    }
                                })
                                
                                newUser.save((myerr, mydat) => {
                                    if (myerr) {
                                        res.send("There was an error, please try again") 
                                }
                                    else {
                                        initiateScores.save((ierr,idata)=>{
                                            if(ierr){
                                                res.send("There was an error, please try again");
                                            }
                                            else{
                                                res.send("Successfully registered");
                                            }
                                        })                                        
                                 }
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
        res.send("Invalid email");
    }

})
app.get("/", (req, res) => {
    res.sendFile(__dirname + "/index.html");
})

// Login Handler
app.post("/login", (req, res) => {
    const username = String(req.body.username).trim();
    const password = String(req.body.password).trim();
    const email = String(req.body.email).trim();
    userLog = username;
    app.get(`/scoreboard/users/${username}`,(rreq,rres)=>{
        JigsawScores.find({},(ferr,fdat)=>{
            let userRoutes = [];
            let userScores;
            for(let mydata of fdat){
                if(mydata.name===userLog){
                    userScores = mydata.scores;
                }
            }
            let modeArray = [];
            let scoreArray = [];
            let nGameArray = [];
            for(score of userScores){
                modeArray.push(score.mode);
                scoreArray.push(score.score);
                nGameArray.push(score.nGame);
            }
            rres.render("scores",{
                modeArray:modeArray,
                scoreArray:scoreArray,
                nGameArray:nGameArray
            })
    
        })
    })
    if(validateEmail(email)){
    JigsawUsers.findOne({ email: email }, (err, data) => {
        if (data === null) {
            res.send("No account");
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
}
else{
    res.send("Invalid email");
}
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
            else{
                res.send("Nohigh");
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
app.post("/scoreboard/users",(req,res)=>{
    let name = req.body.name;
    let mode = req.body.mode;
    let score = req.body.score;
    
   
    JigsawScores.find({name:name},(err,data)=>{
        if(err){
            res.send("parsefloat error");
        }
        else{
            if(data.length===0){
                res.send("length 0");
            }
            else{
                let nGame = data[0].scores.length;
                let modifiedScores = {
                mode:mode,
                score:score,
                nGame:nGame
            }
            JigsawScores.updateOne({name:name},{
                $push:{scores:modifiedScores}
            },(berr,bdat)=>{
                if(berr){
                    
                    res.send("Not updated");
                    
                }
                else{
                    res.send("Saved");
                }
            })
            }
            
        }
    })
    
})



app.listen(process.env.PORT || 3500);
