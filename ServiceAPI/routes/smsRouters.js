const express = require("express");
const smsRoute = express.Router();
const smsController = require("../controllers/SmslController"); //get the sms controller

smsRoute.post("/send", smsController.sendSms); // sms send endpoint

module.exports = smsRoute;