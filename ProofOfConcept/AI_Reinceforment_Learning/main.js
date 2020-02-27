//https://www.sitepoint.com/quick-tip-game-loop-in-javascript/
import { Food } from "./Food.js";

let canvas = document.getElementById("playground");
let context = canvas.getContext("2d");

let food = new Food();

context.fillStyle = 'green';
context.beginPath();
context.arc(10, 10, 5, 0, 2 * Math.PI);
context.fill();

context.fillStyle = 'red';
context.fillRect(20, 20, 15, 15);



