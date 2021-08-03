const express = require('express');
const mongoose = require('mongoose');
const app = express();
const cors = require('cors');
const bcrypt = require('bcrypt');
const saltRounds = 10;
var user = "";
app.use(express.json());
app.use(cors());
app.use(express.urlencoded({extended:true}));
app.use(express.static("static"));
const connection_url = 'mongodb+srv://admin:BoAoAuyCcujHi0Ln@cluster0.qni0g.mongodb.net/NarutoDB?retryWrites=true&w=majority';
mongoose.connect(connection_url,{useNewUrlParser:true,useUnifiedTopology:true,useCreateIndex:true});
const JigsawSchema = mongoose.Schema({
    name:String,
    password:String,
    email:String,
    highScore:Number
})
const JigsawUsers = mongoose.model("jigsawUser",JigsawSchema);
app.post("/register",(req,res)=>{
    const username = req.body.username;
    const password = req.body.password;
    const email = req.body.email;
    console.log(req.body);
    
    JigsawUsers.findOne({name:username},(err,data)=>{
        if(data===null){
            JigsawUsers.findOne({email:email},(err1,data1)=>{
                if(data1===null){
                    bcrypt.hash(password,saltRounds,(error,hash)=>{
                        if(error){res.send("There was an error, please try again")}
                        else{
                            const newUser = new JigsawUsers({
                                name:username,
                                password:hash,
                                email:email,
                                highScore:0
                            })
                            newUser.save((myerr,mydat)=>{
                                if(myerr){res.send("There was an error, please try again")}
                                else{res.send("Successfully registered")}
                            })
                        }
                    })
                }
                else if(data1!=null){
                    res.send("Account with the given email already exists");
                }
            })
        }
        else if(data!=null){
            res.send("Username is already taken");
        }
    })
    
})
app.get("/",(req,res)=>{
    res.sendFile(__dirname+"/index.html");
})
app.post("/login",(req,res)=>{
    const username = req.body.username;
    const password = req.body.password;
    const email=req.body.email;
    JigsawUsers.findOne({email:email},(err,data)=>{
        if(data===null){
            res.send("No account exists with that email");
        }
        else if(data!==null){
            if(username!=data.name){
                res.send("Invalid username");
            }
            else if(username===data.name){
                bcrypt.compare(password,data.password,(error,result)=>{
                    if(result===true){
                        user = username;
                        res.send("Success");
                    }
                    else{
                        res.send("Invalid password");
                    }
                })
            }
        }
    })
})
app.get("/login",(req,res)=>{
    res.send(user);
})
app.post("/scoreboard",(req,res)=>{
    console.log(req.body);
    var score = req.body.score;
    var scr;
    score = parseFloat(score);
    JigsawUsers.findOne({name:req.body.username},(err,data)=>{
        if(data==null){
            res.send("Error");
        }
        else{
            scr = data.highScore;
            if(score>scr){
                JigsawUsers.updateOne({name:req.body.username},{
                    $set:{
                        highScore:score
                    }
                },(error,data)=>{
                    if(error){
                        res.send("Error");
                    }
                    else{
                        res.send("Updated");
                    }
                })
            }
        }
    })
    
});

app.get("/scoreboard",(req,res)=>{
    let dataToSend = [];
    JigsawUsers.find().sort('-highScore').exec((err,data)=>{
        if(err){
            res.send("Error");
        }
        else{
            const len = data.length;
            for(let i=0;i<len;i++){
                dataToSend.push({
                    username:data[i].name,
                    highScore:data[i].highScore
                })
            }
            res.send(dataToSend);
        }
    })
    
})
app.listen(process.env.PORT||3500);