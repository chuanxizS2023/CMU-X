import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(destination: Text("New View")) {
                    Text("Go to new view")
                }
            }
            .navigationBarTitle("Main View", displayMode: .inline)
            .navigationBarItems(
                leading: Button(action: {
                    print("Leading button pressed")
                }) {
                    Image(systemName: "bell.fill")
                },
                trailing: Button(action: {
                    print("Trailing button pressed")
                }) {
                    Image(systemName: "gearshape.fill")
                }
            )
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
