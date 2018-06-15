export class Comment {
  id: number;
  parent: {
    id: number
  };
  children: Comment[] = new Array();
  authorId: number;
  addDate: Date;
  articleId: number;
  content: string;
  rating: number;
  hidden: boolean = false;

  constructor() { }
}
