# SM Builder

The goal of this project is to build a collection of content specific social media accounts (think 100+). Nobody has time to manage and curate content for 100+ social media accounts so the goal of this project is to fix that.

Currently the project supports grabbing content from Reddit, storing it and then posting it to Instagram. An example account is @everythingawwww on Instagram. This account gets the top 10 posts from /r/aww over a period of 24 hours, posts and then repeats.
Social media accounts can post content from either a single subreddit or a mixture of subreddits.

Finished
--------
- Download content and store from reddit (v.redd.it, i.imgur.com, i.redd.it)
  - Uses youtube-dl and ffmpeg
- MySQL storing and retrieving of Instagram account info
- MySQL storage of content
- Follow user, post picture, post story and post video all supported.
- Automatic schedule for retrieving content from reddit and posting to Instagram
- Supports unlimited accounts

Blockage
--------
As I finished the first beta build of this project I found out that Instagram recently (10/9/2018) came down hard on any form of botting. I did not plan to gain followers or spam, just post content but was still met with bans and phone verification blocks. It seems that this project will have to focus on only one account as Instagram detects my program if I am using multiple accounts.

