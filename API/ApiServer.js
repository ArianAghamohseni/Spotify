const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const port = 3000

// Store user data in memory
let users = []

app.use(bodyParser.json())

// Create user account endpoint
app.post('/api/users', (req, res) => {
  let user = req.body
  user.id = users.length + 1
  users.push(user)
  console.log(`Created user with ID ${user.id}`)
  res.status(201).json(user)

  // Download sample song and add to user's liked songs
  const https = require('https')
  const fs = require('fs')
  const artist = 'Artist Name'
  const album = 'Album Name'
  const fileName = 'song.mp3'
  const filePath = '/path/to/your/files/' + artist + '/' + album + '/' + fileName
  const fileUrl = 'http://example.com/song.mp3'

  const file = fs.createWriteStream(filePath)
  const request = https.get(fileUrl, function(response) {
    response.pipe(file)
    file.on('finish', function() {
      file.close()

      const song = {
        title: fileName,
        artist: artist,
        album: album,
        file_path: filePath
      }

      let userId = user.id
      let likedSongs = users[userId - 1].liked_songs
      likedSongs.push(song)
      console.log(`Added song '${fileName}' to user ${userId}'s liked songs`)
    })
  })
})

// Add a liked song endpoint
app.post('/api/users/:id/liked_songs', (req, res) => {
  const userId = parseInt(req.params.id)
  const song = req.body

  let user = users[userId - 1]
  if (!user) {
    res.status(404).send('User not found')
  } else {
    let likedSongs = user.liked_songs
    song.id = likedSongs.length + 1
    likedSongs.push(song)
    console.log(`Added song '${song.title}' to user ${userId}'s liked songs`)
    res.status(201).json(song)
  }
})

// Get a user's liked songs endpoint
app.get('/api/users/:id/liked_songs', (req, res) => {
  const userId = parseInt(req.params.id)

  let user = users[userId - 1]
  if (!user) {
    res.status(404).send('User not found')
  } else {
    res.json(user.liked_songs)
  }
})

// Play a liked song endpoint
app.get('/api/songs/:id/play', (req, res) => {
  const songId = parseInt(req.params.id)

  let song = null
  for (let user of users) {
    let likedSongs = user.liked_songs
    for (let likedSong of likedSongs) {
      if (likedSong.id === songId) {
        song = likedSong
        break
      }
    }
    if (song) {
      break
    }
  }

  if (!song) {
    res.status(404).send('Song not found')
  } else {
    const filePath = song.file_path

    const { spawn } = require('child_process');
    const player = spawn('afplay', [filePath]);

    player.on('close', () => {
      console.log(`Finished playing ${song.title}`)
      res.status(200).send(`Finished playing ${song.title}`)
    })
  }
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
