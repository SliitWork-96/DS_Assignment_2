const express = require("express");
const userRouter = express.Router();
const UserController = require('../controllers/UserController'); // get the user controller

userRouter.post("/sign-up", UserController.user_signup); // user sign up endpoint
userRouter.post('/sign-in', UserController.userSignin);  // user sign in endpoint

module.exports = userRouter;