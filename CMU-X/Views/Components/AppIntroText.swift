import SwiftUI

struct AppIntroText: View {
    @State private var displayedText = ""
    @State private var timer: Timer? = nil
    let fullText: String
    
    var body: some View {
        Text(displayedText)
            .font(Font.custom("YoungSerif-Regular", size: 35))
            .frame(width: 350.0)
            .onAppear(perform: startTypingAnimation)
            .onDisappear(perform: stopTypingAnimation)
    }
    
    func startTypingAnimation() {
        var charIndex = 0
        timer = Timer.scheduledTimer(withTimeInterval: 0.1, repeats: true) { timer in
            if charIndex < fullText.count {
                displayedText.append(fullText[fullText.index(fullText.startIndex, offsetBy: charIndex)])
                charIndex += 1
            } else {
                timer.invalidate()
            }
        }
    }
    
    func stopTypingAnimation() {
        timer?.invalidate()
    }
}

struct AppIntroText_Previews: PreviewProvider {
    static var previews: some View {
        AppIntroText(fullText: "Expand your CMU-wide network")
    }
}
