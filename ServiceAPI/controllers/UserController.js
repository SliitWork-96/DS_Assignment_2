const mongoose = require("mongoose");
const bcrypt = require('bcrypt');
const User = require('../model/user');


/* 
 * post request calls to the user_signup in userController class to insert a new user 
 */
exports.user_signup = (req,res,next) => {

    const {Username} = req.body;

    //find whether User Already exist or not
    User.find({Username})
        .exec()
        .then(user => {
            
            if(user.length >= 1){
                return res.json({
                    message: 'User Already exist'
                });

            }else{

                /* 
                 * hash the password
                 */
                bcrypt.hash(req.body.Password,10,(err, hash) =>{
                    if(err){
                        return res.status(500).json({
                            error: err
                        })
                    }else{
                        /* 
                         * Create the User Schema
                         */
                        
                        const nwuser = new User({
                            _id:mongoose.Types.ObjectId(),
                            Type: "User",
                            Username: req.body.Username,
                            Password: hash
                        });

                        nwuser
                            .save()
                            .then(result => {
                                console.log('User Created',result);
                                res.status(200).json({
                                    success:true,
                                    message: 'User Created'
                                })
                            })
                            .catch( err =>{
                                console.log(err);
                                res.status(500).json({
                                    error: err
                                })
                            });
                    }
                });
            }
        })
        .catch( err =>{
            console.log(err);
            res.status(500).json({
                error: err
            })
        });
}



/* 
 * post request calls to the user Signin in userController class to login  
 */

exports.userSignin =(req,res,next) => {

    User.find({Username:req.body.Username}).exec().then(user => {
        
        if(user.length < 1){
            return res.send({message:'unauthorized User!!!!'});
        }

        /* 
         * Compare the entered passwaord with exisiting password
         */
        bcrypt.compare(req.body.Password, user[0].Password,(err,result) => {
            if(err){
                return res.send({message :'Password does not match!!!!!'});
            }
            if(result){
             
                user[0]._id = user[0]._id;
                user[0].LastLogin = Date.now();
                user[0]
                    .save()
                    .then(result => {
                        console.log("User - "+user[0]._id+" Signed-in, Time - "+Date.now());
                    })
                    .catch( err =>{
                        console.log(err);
                    });
                    
                return res.status(200).json({
                    success:true,
                    message:'Authentication successful',
                });
            }else{
                return res.status(401).send('UNAUTHORIZED');
            }
        })
    });
}

