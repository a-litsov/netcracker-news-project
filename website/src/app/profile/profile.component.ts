import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {ProfileService} from "./profile.service";
import {Profile} from "./profile";
import {Preview} from "../preview";
import {ArticlesService} from "../articles.service";
import {Location} from "@angular/common";
import {Comment} from "../comment";
import {CommentsService} from "../comments.service";
import {AuthService} from "../auth/auth.service";
import {UserService} from "../user-settings/user.service";
import {Role} from "../user-settings/role";
import {Category} from "../category";
import {User} from "../auth/user";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  private userId: number;
  public profile: Profile;

  private previews: Preview[] = [];
  private comments: Comment[] = [];

  private articlesNetworkProblem: boolean = false;
  private commentsNetworkProblem: boolean = false;

  public hasVoted: boolean = true;
  public banned: boolean = false;
  public muted: boolean = false;

  public allRoles: Role[] = [];
  public role: Role = new Role();

  constructor(private route: ActivatedRoute, private profileService: ProfileService,
              private articlesService: ArticlesService, private commentsService: CommentsService,
              private location: Location, private cdRef:ChangeDetectorRef,
              private authService: AuthService, public usersService: UserService) { }

  private loadArticlesPreview(id: number) {
    this.articlesService.getPreviewByAuthorId(id).subscribe((inPreviews: Preview[]) => {
        this.previews = [...inPreviews];
        console.log(this.previews);
        this.articlesNetworkProblem = false;
      },
      error => {
        this.articlesNetworkProblem = true;
        console.log("Error while obtaining all articles: ", error)
      }
    );
  }


  private deleteArticleById(id: number) {
    console.log("removing article...");
    console.log(id);
    this.articlesService.deleteArticleById(id).subscribe((response) => {
      console.log(response);
      window.location.reload();
    });
  }

  private loadComments(id: number) {
    this.commentsService.getCommentsByAuthorId(id).subscribe((inComments: Comment[]) => {
        this.comments = [ ... inComments];
        console.log(this.comments);
        this.commentsNetworkProblem = false;
      },
      error => {
        console.log("Error while obtaining comments: ", error);
        this.commentsNetworkProblem = true;
      }
    );
  }

  getRankColor() {
    return "#" + this.profile.rank.color;
  }

  loadProfile(id: number) {
    this.profileService.getProfileById(id).subscribe((profile: Profile) => {
      console.log("get profile", profile);
      this.profile = profile;
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.userId = parseInt(params.get('id'));
      console.log("profile id to load", this.userId);
      this.loadProfile(this.userId);

      console.log("loading hasVoted...");
      this.profileService.hasVoted(this.userId).subscribe((voted: boolean) => {
        this.hasVoted = voted;
        console.log("voted is", voted);
      });

      console.log("loading muted");
      this.usersService.userMuted(this.userId).subscribe((muted: boolean) => {
        this.muted = muted;
        console.log("muted is", muted);
      });

      console.log("loading banned");
      this.usersService.userBanned(this.userId).subscribe((banned: boolean) => {
        this.banned = banned;
        console.log("banned is", banned);
      });

      this.usersService.getAllRoles().subscribe((roles: Role[]) => {
        this.allRoles = roles;
        console.log("roles are", roles);
      });

      this.usersService.getUserRole(this.userId).subscribe((role: Role) => {
        this.role = role;
        console.log("role is", role);
      });

      console.log("loading previews");
      this.loadArticlesPreview(this.userId);

      console.log("loading comments of 1st article");
      this.loadComments(this.userId);
    });
  }

  canVoteComment(): boolean {
    return this.authService.hasAuthority('OP_VOTE_COMMENT') &&
      this.authService.user.userId != this.userId;
  }

  userVoted(ratingObj: {rating: number}) {
    console.log(ratingObj.rating);
    this.profileService.vote(this.userId, ratingObj.rating).subscribe((newRating: number) => {
      console.log("successfully voted, new rating is", newRating);
      this.profile.rating = newRating;
      this.hasVoted = true;
    }, (error) => console.log("error while voting", error));
  }

  canVote(): boolean {
    return this.authService.hasAuthority("OP_VOTE_PROFILE") && !this.hasVoted;
  }

  banUser() {
    console.log("banning user", this.userId);
    this.usersService.banUser(this.userId).subscribe((role: Role) => {
      console.log("user banned");
      this.banned = true;
      this.role = role;
    })
  }

  unbanUser() {
    console.log("unbanning user", this.userId);
    this.usersService.unbanUser(this.userId).subscribe((role: Role) => {
      console.log("user banned");
      this.banned = false;
      this.role = role;
    })
  }

  muteUser() {
    console.log("muting user", this.userId);
    this.usersService.muteUser(this.userId).subscribe((role: Role) => {
      console.log("user muted");
      this.muted = true;
      this.role = role;
    })
  }

  unmuteUser() {
    console.log("unmuting user", this.userId);
    this.usersService.unmuteUser(this.userId).subscribe((role: Role) => {
      console.log("user unmuted");
      this.muted = false;
      this.role = role;
    })
  }

  compareRoles(first: Role, second: Role) {
    return first.id === second.id && first.authority === second.authority;
  }

  changeRole() {
    this.usersService.setUserRole(this.userId, this.role).subscribe((user) => {
      console.log("updated role", user);
      this.role = user['role'];
    })
  }
}
