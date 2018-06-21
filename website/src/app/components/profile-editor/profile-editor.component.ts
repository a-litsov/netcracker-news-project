import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {CommentsService} from "../../services/comments.service";
import {ProfileService} from "../../services/profile.service";
import {ArticlesService} from "../../services/articles.service";
import {Location} from "@angular/common";
import {Profile} from "../profile/profile";
import {Category} from "../article/category";

@Component({
  selector: 'app-profile-editor',
  templateUrl: './profile-editor.component.html',
  styleUrls: ['./profile-editor.component.css']
})
export class ProfileEditorComponent implements OnInit {

  private userId: number;
  public profile: Profile = new Profile();
  public genders: string[];

  constructor(private route: ActivatedRoute, private profileService: ProfileService,
              private location: Location, private cdRef:ChangeDetectorRef,
              private authService: AuthService, private router: Router) {
  }

  loadProfile(id: number) {
    this.profileService.getProfileById(id).subscribe((profile: Profile) => {
      console.log("get profile", profile);
      this.profile = profile;
      this.profile.birthDate = (profile.birthDate == null) ? null : new Date(profile.birthDate);
    });
  }

  updateProfile() {
    console.log("sent profile", this.profile);
    this.profileService.updateProfile(this.userId, this.profile).subscribe((profile: Profile) => {
      this.router.navigateByUrl("/profile/" + this.userId);
    });
  }

  cancel() {
    this.location.back();
  }

  loadGenders() {
    this.profileService.getGenders().subscribe((genders: string[]) => {
      this.genders = genders;
      console.log("genders are", genders);
    })
  }

  ngOnInit() {
    if (this.authService.isLoggedOut()) {
      this.router.navigateByUrl("/auth");
    }
    this.route.paramMap.subscribe((params: ParamMap) => {
      if (params.get('id') == undefined) {
        console.log("id undefined");
        this.userId = this.authService.user.userId;
        console.log("auth id used", this.userId);
      } else {
        this.userId = parseInt(params.get('id'));
        if (this.userId != this.authService.user.userId &&
              !this.authService.hasAuthority("OP_UPDATE_PROFILE")) {
          this.router.navigateByUrl("/access-denied");
        }
      }
      console.log("profile id to load", this.userId);
      this.loadProfile(this.userId);
    });

    this.loadGenders();
  }
}
