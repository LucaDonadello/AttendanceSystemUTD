import express from 'express';
import { getStudents } from './database.js';

const app = express();

//add link in browser: http://localhost:3000/test --modify to send to the browser the result of the query
app.get('/test', async (req, res) => {
    const students = await getStudents();
    res.send(students);
});


//error andling and listening
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something broke!');
});

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});