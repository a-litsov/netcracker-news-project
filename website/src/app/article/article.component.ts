import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../articles.service';
import {Article} from "../article";
import {Router, ActivatedRoute, ParamMap} from "@angular/router";
import {CommentsService} from "../comments.service";
import {Comment} from '../comment';
import {AuthService} from "../auth/auth.service";
import {UserService} from "../user-settings/user.service";
import {Profile} from "../profile/profile";
import {Vote} from "../vote";
import {ProfileService} from "../profile/profile.service";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  public article: Article;
  private comments: Comment[] = [];
  private authorsIds: number[] = [];
  private parentComment: Comment;
  private userComment: Comment = new Comment();
  private authorsInfo = {};
  private networkProblem: boolean = false;
  public profile: Profile;
  public authorProfile: Profile = new Profile();
  private votes = {};
  private articleVote: Vote = new Vote();

  constructor(private articlesService: ArticlesService, private commentsService: CommentsService,
              private authService: AuthService, private route: ActivatedRoute,
              private router: Router, private usersService: UserService,
              private profileService: ProfileService) { }

  private loadArticle(id: number) {
    this.articlesService.getArticleById(id).subscribe((inArticle: Article) => {
        this.article = {...inArticle};
        console.log(this.article);
        this.profileService.getProfileById(this.article.authorId).subscribe((profile) => console.log("got author profile", this.authorProfile = profile));
        console.log("loading comments begins..");
        this.loadComments();
      },
      error => console.log("Error while obtaining article: ", error)
    );
  }

  private loadComments() {
    let articleId: number = this.article.id;
    this.commentsService.getRootCommentsByArticleId(articleId).subscribe((inComments: Comment[]) => {
      this.comments = inComments;
      console.log(this.comments);
      this.networkProblem = false;
    },
      error => {
        this.networkProblem = true;
        console.log("Error while obtaining comments: ", error)
      }
    );
    this.commentsService.getAuthorsIdsByArticleId(articleId).subscribe((authorsIds: number[]) => {
        this.authorsIds = authorsIds;
        console.log(this.authorsIds);
        this.networkProblem = false;
        this.authorsIds.push(this.article.authorId);
        console.log("after pushing article author", this.authorsIds);
        this.usersService.getAuthorsCommentInfoByArticleId(articleId, this.authorsIds).subscribe((authorsInfo) => {
            this.authorsInfo = authorsInfo;
            console.log(this.authorsInfo);
            this.networkProblem = false;
          },
          error => {
            this.networkProblem = true;
            console.log("Error while obtaining authors comment info: ", error)
          }
        );
      },
      error => {
        this.networkProblem = true;
        console.log("Error while obtaining authors ids: ", error)
      }
    );
    this.commentsService.getMyVotes(this.article.id).subscribe((votes) => {
      this.votes = votes;
      console.log("got votes", votes);
    }, (error) => console.log("error while obtaining votes"));
  }

  likeComment(comment: Comment) {
    this.commentsService.likeComment(comment.id).subscribe((rating: number) => {
      comment.rating = rating;
      console.log("liked comment, current rating", rating);
      if (this.votes[comment.id] == undefined || this.votes[comment.id].type == "DISLIKE") {
        let vote: Vote = new Vote();
        vote.type = "LIKE";
        this.votes[comment.id] = vote;
      } else {
        delete this.votes[comment.id];
      }
    })
  }

  dislikeComment(comment: Comment) {
    this.commentsService.dislikeComment(comment.id).subscribe((rating: number) => {
      comment.rating = rating;
      console.log("disliked comment, current rating", rating);
      if (this.votes[comment.id] == undefined || this.votes[comment.id].type == "LIKE") {
        let vote: Vote = new Vote();
        vote.type = "DISLIKE";
        this.votes[comment.id] = vote;
      } else {
        delete this.votes[comment.id];
      }
    })
  }

  likeArticle() {
    this.articlesService.likeArticle(this.article.id).subscribe((rating: number) => {
      this.article.rating = rating;
      console.log("liked article, current rating", rating);
      if (this.articleVote == undefined || this.articleVote.type == "DISLIKE") {
        let vote: Vote = new Vote();
        vote.type = "LIKE";
        this.articleVote = vote;
      } else {
        this.articleVote = undefined;
      }
    })
  }

  dislikeArticle() {
    this.articlesService.dislikeArticle(this.article.id).subscribe((rating: number) => {
      this.article.rating = rating;
      console.log("disliked article, current rating", rating);
      if (this.articleVote == undefined || this.articleVote.type == "LIKE") {
        let vote: Vote = new Vote();
        vote.type = "DISLIKE";
        this.articleVote = vote;
      } else {
        this.articleVote = undefined;
      }
    })
  }

  setParentComment(parent: Comment) {
    this.userComment.content = this.authorsInfo[parent.authorId].username + ", ";
    this.parentComment = parent;

    window.scrollTo(0, document.body.scrollHeight);
  }

  private createComment() {
    this.userComment.authorId = this.authService.user.userId;
    this.userComment.articleId = this.article.id;
    if (this.parentComment != null) {
      this.userComment.parent = {id: this.parentComment.id};
    }
    console.log("posting comment...", this.userComment);
    this.commentsService.createComment(this.userComment).subscribe((inComment: Comment) => {

      this.authorsInfo[this.authService.user.userId] = {
        id: this.authService.user.userId,
        username: this.authService.user.userName,
        avatarUrl: this.profile.avatarUrl
      }

      if (this.parentComment != null) {
        this.parentComment.children.push(inComment);
      } else {
        this.comments.push(inComment);
      }
      console.log(inComment);
    });
  }

  private hideComment(comment: Comment) {
    console.log("hiding comment...", comment);
    this.commentsService.hideComment(comment.id).subscribe(() => {
      console.log("comment hidden", comment);
      comment.hidden = true;
    });
  }

  private showComment(comment: Comment) {
    console.log("showing comment...", comment);
    this.commentsService.showComment(comment.id).subscribe(() => {
      console.log("comment showed", comment);
      comment.hidden = false;
    });
  }

  private deleteComment(comment: Comment, parent: Comment) {
    console.log("deleting comment...", comment);
    this.commentsService.deleteComment(comment.id).subscribe(() => {
      console.log("comment deleted", comment);
      if (parent == undefined) {
        this.comments = this.comments.concat(comment.children);
        this.comments.splice(this.comments.indexOf(comment), 1);
      } else {
        parent.children = parent.children.concat(comment.children);
        parent.children.splice(parent.children.indexOf(comment), 1);
      }
      if (this.parentComment == comment) {
        this.parentComment = null;
      }
    });
  }

  getLikeButtonColor(comment: Comment) {
    let vote: Vote = this.votes[comment.id];
    if (vote == undefined) {
      return "black";
    }
    if (vote.type == "LIKE") {
      return "green";
    }
  }

  getDislikeButtonColor(comment: Comment) {
    let vote: Vote = this.votes[comment.id];
    if (vote == undefined) {
      return "black";
    }
    if (vote.type == "DISLIKE") {
      return "INDIANRED";
    }
  }

  getArticleLikeButtonColor() {
    let vote: Vote = this.articleVote;
    if (vote == undefined) {
      return "black";
    }
    if (vote.type == "LIKE") {
      return "green";
    }
  }

  getArticleDislikeButtonColor() {
    let vote: Vote = this.articleVote;
    if (vote == undefined) {
      return "black";
    }
    if (vote.type == "DISLIKE") {
      return "INDIANRED";
    }
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.articlesService.getMyVote(parseInt(params.get('id'))).subscribe((v) => {
        this.articleVote = v;
        console.log("loaded article vote", v);
      });

      this.loadArticle(parseInt(params.get('id')));
    });

    // TODO: change the way of accessing user avatar; the root of many errors; look in another classes when will fix
    this.authService.getProfile().subscribe((profile) => console.log(this.profile = profile));

  }

}
