import { Component } from '@angular/core';
import {Article} from "../Article";
import {ArticleService} from "../article.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent {
  articles!: Article[];
  selectedArticle!: Article;

  constructor(
      private articleService: ArticleService,
      private router: Router
  ) {
    this.getAllArticles();
  }

  public getAllArticles(){
    this.articleService.getAllArticles().subscribe((value) => {
      this.articles = value;
    });
  }

  public getArticleById(id: number) {
    this.articleService.getArticleById(id).subscribe((value) => {
      this.selectedArticle = value;
    });
  }

  public updateArticle(article: Article) {
    this.articleService.updateArticle(article).subscribe();

  }

  public deleteArticle(article: Article) {
    this.articleService.deleteArticle(article).subscribe();
  }

  public createArticle(article: Article) {
    this.articleService.createArticle(article).subscribe();
  }

  openArticleDetails(article: Article) {
    this.router.navigate(['/articles/'+article.id],
        {state: {article: article, solo: true}})
  }

  openCreateArticle() {
    this.router.navigate(['/articles/edit'],
        {state: {creating: true}})
  }
}
