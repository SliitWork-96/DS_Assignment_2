const express = require("express");
const EmailRoute = express.Router();
const EmailController = require("../controllers/EmailController"); //get the email controller

EmailRoute.post("/send", EmailController.sendEmail); // email send endpoint

module.exports = EmailRoute;