
import { fakeAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlesDashbComponent } from './articles-dashb.component';

describe('ArticlesDashbComponent', () => {
  let component: ArticlesDashbComponent;
  let fixture: ComponentFixture<ArticlesDashbComponent>;

  beforeEach(fakeAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticlesDashbComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticlesDashbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
