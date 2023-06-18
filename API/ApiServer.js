// Import necessary modules
const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const User = require('./models/user');

// Create Express app and configure middleware
const app = express();
app.use(bodyParser.json());

// Connect to MongoDB database
mongoose.connect('mongodb://localhost/spotify', { useNewUrlParser: true });

// Define route for creating a new user account
app.post('/api/users', async (req, res) => {
  try {
    // Extract user data from the request body
    const { name, email, password } = req.body;

    // Validate user data
    if (!name || !email || !password) {
      return res.status(400).json({ message: 'All fields are required' });
    }

    // Check if user already exists in the database
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(409).json({ message: 'Email address is already in use' });
    }

    // Create new user and save to the database
    const user = new User({ name, email, password });
    await user.save();

    // Return success response with user data
    res.status(201).json({ message: 'Account created successfully', user });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error occurred' });
  }
});

// Start server on port 3000
app.listen(3000, () => {
  console.log('Server listening on port 3000');
});