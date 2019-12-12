//
//  NoteDetailViewController.swift
//  Scheducation iOS
//
//  Created by Jason Inirio on 12/11/19.
//  Copyright © 2019 Jason Inirio. All rights reserved.
//

import UIKit

class NoteDetailViewController: UIViewController {
    
    var name: String = ""
    
    @IBOutlet weak var noteName: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue" {
            name = noteName.text!
        }
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
