import SwiftUI

struct SignInSignUpForm: View {
    @State private var isSignIn: Bool = true
    @State private var username: String = ""
    @State private var password: String = ""
    
    var body: some View {
        VStack {
            VStack {
                TextField("Username/Email", text: $username)
                    .padding()
                    .background(Color.white.opacity(0.8)) // Adjusted opacity for visibility
                    .cornerRadius(10)
                    .shadow(radius: 5)
                    .foregroundColor(.black) // Set text color to ensure visibility
                
                SecureField("Password", text: $password)
                    .padding()
                    .background(Color.white.opacity(0.8)) // Adjusted opacity for visibility
                    .cornerRadius(10)
                    .shadow(radius: 5)
                    .foregroundColor(.black) // Set text color to ensure visibility
            }
            .padding()
            HStack(spacing:20){
                Button(action: {
                    // Action for Sign In
                }) {
                    Text("Sign In")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.gray)
                        .cornerRadius(10)
                }
                Button(action: {
                    // Action for Sign Up
                }) {
                    Text("Sign Up")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.gray)
                        .cornerRadius(10)
                }
            }
                
        }
        .background(Color.gray.opacity(0.5).blur(radius: 5))
        .cornerRadius(15)
        .padding()
        .frame(maxWidth: 400) // Restricting the width
    }
}


struct SignInSignUpForm_Previews: PreviewProvider {
    static var previews: some View {
        SignInSignUpForm()
    }
}
