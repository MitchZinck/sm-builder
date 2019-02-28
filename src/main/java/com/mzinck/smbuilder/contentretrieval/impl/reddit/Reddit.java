package com.mzinck.smbuilder.contentretrieval.impl.reddit;

import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieveHandler;
import com.mzinck.smbuilder.contentretrieval.Tag;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.*;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.ratelimit.RateLimiter;

import java.util.ArrayList;

/**
 * Retrieves content from Reddit.com
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Reddit implements ContentRetrieveHandler {

    /**
     * The @Tag that is needed for specific content retrieval.
     */
    private Tag tag;

    private String username = "";
    private String password = "";
    private String clientId = "";
    private String clientSecret = "";

    /**
     * Constructor that sets reddit api login info.
     * @param username the reddit api account username.
     * @param password the reddit api account password.
     * @param clientId the reddit api account client id.
     * @param clientSecret the reddit api client secret.
     */
    public Reddit(String username, String password, String clientId, String clientSecret) {
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Retrieves top 10 pieces of content in the last 24 hours.
     */
    @Override
    public ArrayList<Content> getContent() {
        UserAgent userAgent = new UserAgent("SMBuilder", "com.mzinck.smbuilder", "v0.1", username);
        Credentials credentials = Credentials.script(username, password, clientId, clientSecret); //clientid client secret
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient reddit = OAuthHelper.automatic(adapter, credentials);

        ArrayList<Content> content = new ArrayList<Content>();
        for(String subreddit : tag.getTags()) {
            DefaultPaginator<Submission> paginator = reddit.subreddit(subreddit)
                    .posts()
                    .limit(20)//if there are more than 1 subreddit to pull from then pull less per sub
                    .sorting(SubredditSort.TOP)
                    .timePeriod(TimePeriod.DAY)
                    .build();
            // Request the first page
            Listing<Submission> firstPage = paginator.next();
            for (Submission post : firstPage) {
                if(post.isNsfw()) {
                    continue;
                } else if(post.getScore() < 2500) {
                    continue;
                }
                if ((post.getDomain().contains("i.imgur.com") || post.getDomain().contains("i.redd.it") ||
                        post.getDomain().contains("i.redditmedia.com") || post.getDomain().contains("v.redd.it") || post.getDomain().contains("thumbs.gfycat.com"))
                        && !post.isSelfPost()) {
                   // System.out.println(String.format("%s (/r/%s, %s points) - %s",
                         //   postPicture.getTitle(), postPicture.getSubreddit(), postPicture.getScore(), postPicture.getUrl()));
                    String url = post.getUrl();
                    Content c = new Content(0, post.getTitle(), url, post.getSubreddit(), false, post.getScore());
                    content.add(c);
                }
            }
        }
        return content;
    }

    /**
     * Gets a recent Reddit post to then post to an instagram story.
     */
    public Content getStory() {
        UserAgent userAgent = new UserAgent("SMBuilder", "com.mzinck.smbuilder", "v0.1", username);
        Credentials credentials = Credentials.script(username, password, clientId, clientSecret); //clientid client secret
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient reddit = OAuthHelper.automatic(adapter, credentials);

        ArrayList<Content> content = new ArrayList<Content>();
        for (String subreddit : tag.getTags()) {
            DefaultPaginator<Submission> paginator = reddit.subreddit(subreddit)
                    .posts()
                    .limit(10)
                    .sorting(SubredditSort.TOP)
                    .timePeriod(TimePeriod.HOUR)
                    .build();
            // Request the first page
            Listing<Submission> firstPage = paginator.next();
            for (Submission post : firstPage) {
                if(post.isNsfw()) {
                    continue;
                }
                if ((post.getDomain().contains("imgur.com") || post.getDomain().contains("i.redd.it") ||
                        post.getDomain().contains("i.redditmedia.com")) && (post.getUrl().contains(".jpg") || post.getUrl().contains(".png"))) {
                    // System.out.println(String.format("%s (/r/%s, %s points) - %s",
                    //   postPicture.getTitle(), postPicture.getSubreddit(), postPicture.getScore(), postPicture.getUrl()));
                    Content c = new Content(0, post.getTitle(), post.getUrl(), post.getSubreddit(), false, post.getScore());
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Sets the tag associated with the content to retrieve.
     *
     * @param tag the content to retrive
     */
    @Override
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * @return the @Tag associated with the content.
     */
    @Override
    public Tag getTag() {
        return tag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
