import SwiftUI

// 1. Sleek Gradient Button
struct SleekButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(LinearGradient(gradient: Gradient(colors: [Color.gray.opacity(0.6), Color.gray.opacity(0.9)]), startPoint: .top, endPoint: .bottom))
            .cornerRadius(10)
            .shadow(color: Color.black.opacity(0.2), radius: 5, x: 0, y: 5)
            .foregroundColor(.white)
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

// 2. Flat Design Button
struct FlatButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(Color.orange)
            .cornerRadius(5)
            .foregroundColor(.white)
            .overlay(
                RoundedRectangle(cornerRadius: 5)
                    .stroke(Color.white, lineWidth: 2)
            )
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

// 3. 3D Embossed Button
struct EmbossedButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(Color.gray)
            .cornerRadius(10)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.gray, lineWidth: 3)
                    .shadow(color: .black, radius: 3, x: 3, y: 3)
                    .clipShape(RoundedRectangle(cornerRadius: 10))
            )
            .foregroundColor(.white)
            .scaleEffect(configuration.isPressed ? 0.9 : 1.0)
    }
}
// 4. CMU Tartan Plaid Button
struct TartanButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding()
            .background(
                Image("CMU_Tartan_Pattern") // Assuming you have a CMU tartan pattern image
                    .resizable()
            )
            .foregroundColor(.white)
            .cornerRadius(10)
            .shadow(radius: 3)
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

// 5. CMU Badge Button
struct BadgeButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(Color.red)
            .overlay(
                Image("CMU_Badge_Logo") // Assuming you have a CMU badge or logo
                    .resizable()
                    .frame(width: 40, height: 40)
                    .opacity(0.5)
            )
            .foregroundColor(.white)
            .cornerRadius(15)
            .shadow(radius: 5)
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

// 6. CMU Embossed Button
struct CMUEmbossedButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding()
            .background(Color.gray)
            .cornerRadius(10)
            .overlay(
                Text("CMU")
                    .font(Font.system(size: 20, weight: .bold, design: .rounded))
                    .foregroundColor(Color.gray.opacity(0.3))
            )
            .foregroundColor(.white)
            .scaleEffect(configuration.isPressed ? 0.9 : 1.0)
    }
}

// 7. CMU Modern Flat Button
struct CMUFlatButtonStyle: ButtonStyle {
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 25)
            .padding(.vertical, 12)
            .background(Color.red)
            .cornerRadius(8)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(Color.gray, lineWidth: 1)
            )
            .foregroundColor(.white)
            .font(.system(size: 16, weight: .medium))
            .scaleEffect(configuration.isPressed ? 0.96 : 1.0)
    }
}

// Example usage in your views:
/*
 
 */


struct Button_styles_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing:20){
            Button("Click Me!") {
                // Button Action
            }
            .buttonStyle(SleekButtonStyle())
            
            Button("Action") {
                // Button Action
            }
            .buttonStyle(FlatButtonStyle())
            
            Button("Proceed") {
                // Button Action
            }
            .buttonStyle(EmbossedButtonStyle())
            
            Button("Join CMU-X") {
                // Button Action
            }
            .buttonStyle(TartanButtonStyle())
            
            Button("Learn More") {
                // Button Action
            }
            .buttonStyle(BadgeButtonStyle())
            
            Button("Proceed") {
                // Button Action
            }
            .buttonStyle(CMUEmbossedButtonStyle())
            
            Button("Explore") {
                // Button Action
            }
            .buttonStyle(CMUFlatButtonStyle())
        }
    }
}
