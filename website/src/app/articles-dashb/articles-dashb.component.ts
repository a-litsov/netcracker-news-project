import { Component } from '@angular/core';

@Component({
  selector: 'articles-dashb',
  templateUrl: './articles-dashb.component.html',
  styleUrls: ['./articles-dashb.component.css']
})
export class ArticlesDashbComponent {
  cards = [
    { title: 'Diamondprox: «Январь — безумный месяц для русских. Полмесяца ты пьешь, забывая все про League of Legends»', cols: 1, rows: 1, img: "https://svirtus.cdnvideo.ru/TEXPIKFXmrSdIhxBfgb5LEB9jsc=/0x0:733x412/573x325/filters:quality(95)/https://s3.eu-central-1.amazonaws.com/cybersportru-media/65/65086319865a47481886e7a33aee660d.jpg?m=0360e0fb912de0402401be9c35df1d25"},
    { title: 'Card 2', cols: 1, rows: 1, img: "https://svirtus.cdnvideo.ru/n3B0q1ltl-hXCY1lsopZZ41kafM=/0x0:2045x1150/573x325/filters:quality(95)/https://s3.eu-central-1.amazonaws.com/cybersportru-media/fa/faa8d923d46fad41db7be315a517d889.jpg?m=5bccec0506731ddd27fccb90bb950934"},
    { title: 'Card 3', cols: 1, rows: 1, img: "https://svirtus.cdnvideo.ru/FmJr3RNIfrw2Ffoix70LQyjPpk4=/0x0:1000x600/573x325/filters:quality(95)/https://s3.eu-central-1.amazonaws.com/cybersportru-media/50/50f60331bf1c830248eec1e3a8860e2d.jpg?m=db1e15b86f4f405eb4abe7464fa3027d"},
    { title: 'Card 4', cols: 1, rows: 1, img: "https://svirtus.cdnvideo.ru/TEXPIKFXmrSdIhxBfgb5LEB9jsc=/0x0:733x412/573x325/filters:quality(95)/https://s3.eu-central-1.amazonaws.com/cybersportru-media/65/65086319865a47481886e7a33aee660d.jpg?m=0360e0fb912de0402401be9c35df1d25"}
  ];
}
