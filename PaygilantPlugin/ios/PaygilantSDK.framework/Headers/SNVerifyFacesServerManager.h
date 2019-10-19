//
//  SNVerifyFacesServerManager.h
//  ScanovateImagingDemo
//
//  Created by scanovate pro on 30/08/2016.
//  Copyright © 2016 scanovate. All rights reserved.
//

//#import <QuartzCore/QuartzCore.h>
#import <UIKit/UIKit.h>
//#import <Foundation/Foundation.h>

typedef void (^completion)(NSDictionary* dictComp);

@interface SNVerifyFacesServerManager : NSObject

+ (void)getJSONForModelImage:(UIImage *)modelImage andVerifyImage:(UIImage *)verifyImage completion:(completion)completion;

@end
