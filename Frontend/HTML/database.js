import mysql from 'mysql2'
import dotenv from 'dotenv'
dotenv.config()

const pool = mysql.createPool({
    host: process.env.HOST,
    port: process.env.PORT,
    user: process.env.USER,
    password: process.env.PSW,
    database: process.env.DB
}).promise()

//example add more functions
export async function getStudents() {
    const [result] = await pool.query('SELECT * FROM Student')
    return result
}