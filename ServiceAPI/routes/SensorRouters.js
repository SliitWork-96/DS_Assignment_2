const express = require("express");
const SensorRoute = express.Router();
const SensorController = require("../controllers/SensorController"); //get the sensor controller

SensorRoute.post("/create", SensorController.addSensor);     // sensor create endpoint
SensorRoute.get("/", SensorController.getAllSensor);         // get ALl sensor endpoint
SensorRoute.get("/:id", SensorController.getSensor);         // get Sensor by id endpoint
SensorRoute.put("/update/:id", SensorController.editSensor); // Update Sensor endpoint
SensorRoute.delete("/delete/:id", SensorController.deleteSensor); // delete Sensor endpoint

module.exports = SensorRoute;