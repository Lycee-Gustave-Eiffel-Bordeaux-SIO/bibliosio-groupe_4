import {Component, Input, OnInit} from '@angular/core';
import {Article} from "../Article";
import {ArticleService} from "../article.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css']
})
export class ArticleDetailsComponent implements OnInit{
  @Input() article!: Article
  @Input() solo: boolean = true

  constructor(
      private articleService: ArticleService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.article==null) {
      this.article=history.state.revue
      if (history.state.revue==null) {
        this.articleService.getArticleById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
          .subscribe(value=>this.article=value)
      }
    }
    this.solo=history.state.solo
  }

  delete() {
    this.articleService.deleteArticle(this.article).subscribe(() => this.router.navigate(['/articles']));
  }

  update() {
    this.router.navigate(['/articles/edit'], { state: { article: this.article, updating: true } });
  }
}
