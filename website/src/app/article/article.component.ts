import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../articles.service';
import {Article} from "../article";
import {Router, ActivatedRoute, ParamMap} from "@angular/router";
import {CommentsService} from "../comments.service";
import {Comment} from '../comment';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  article: Article;
  comments: Comment[] = [];
  userComment: Comment = new Comment();

  constructor(private articlesService: ArticlesService, private commentsService: CommentsService,
              private route: ActivatedRoute, private router: Router) { }

  private loadArticle(id: number) {
    this.articlesService.getArticleById(id).subscribe((inArticle: Article) => {
        this.article = {...inArticle};
        console.log(this.article);
        console.log("loading comments begins..");
        this.loadComments();
      },
      error => console.log("Error while obtaining article: ", error)
    );
  }

  private loadComments() {
    let articleId: number = this.article.id;
    this.commentsService.getRootCommentsByArticleId(articleId).subscribe((inComments: Comment[]) => {
        this.comments = [ ... inComments];
        console.log(this.comments);
      },
      error => console.log("Error while obtaining comments: ", error)
    );
  }

  setParentComment(authorName: string, parentCommentId: number) {
    this.userComment.content = authorName + ", ";
    this.userComment.parent = { id: parentCommentId };
    window.scrollTo(0,document.body.scrollHeight);
  }

  private createComment() {
    this.userComment.authorName = "Неизвестный автор";
    this.userComment.articleId = this.article.id;
    console.log("posting comment...", this.userComment);
    this.commentsService.createComment(this.userComment).subscribe((inComment: Comment) => {
      this.userComment = { ... inComment};
      console.log(this.userComment);
      window.location.reload();
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      console.log(params.get('id'));
      this.loadArticle(parseInt(params.get('id')));
    });
  }

}
