import SwiftUI

struct LandingPage: View {
    let fullText = "Expand your CMU-wide network"
    
    var body: some View {
        ZStack {
            // CMU-landing-background as the base layer
            Image("CMU-landing-background")
                .resizable()
                .aspectRatio(contentMode: .fill)
                .edgesIgnoringSafeArea(.all)
            
            VStack(spacing: 20) {
                Image("CMU-X_logo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 150, height: 150)
                    .opacity(0.8)
                
                AppIntroText(fullText: fullText)
                
                SignInSignUpForm()
                    .padding(.bottom, 50)
            }
        }
    }
}

struct LandingPage_Previews: PreviewProvider {
    static var previews: some View {
        LandingPage()
    }
}
