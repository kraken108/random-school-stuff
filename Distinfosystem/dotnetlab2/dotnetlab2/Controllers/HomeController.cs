﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using dotnetlab2.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using dotnetlab2.Models.HomeViewModels;
using dotnetlab2.Data;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace dotnetlab2.Controllers
{

    [Authorize]
    public class HomeController : Controller
    {
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly SignInManager<ApplicationUser> _signInManager;
        private readonly ApplicationDbContext _context;

        public HomeController(UserManager<ApplicationUser> userManager,
          SignInManager<ApplicationUser> signInManager,ApplicationDbContext context)
        {
            _context = context;
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            var user = await _userManager.GetUserAsync(User);
            if (user == null)
            {
                throw new ApplicationException($"Unable to load user with ID '{_userManager.GetUserId(User)}'.");
            }

            //get login information
            var logins = from s in _context.Logins select s;
            
            logins = logins.Where(s => s.ApplicationUser.Id == user.Id);
            var oneMonthAgo = DateTime.Now.AddMonths(-1);
            logins = logins.Where(s => s.DateTime > oneMonthAgo);
            var loginsThisMonth = logins.Count();

            //Get number of unread messages
            var messages = from s in _context.Messages select s;
            messages = messages.Where(s => s.Receiver.Id == user.Id);
            messages = messages.Where(s => s.IsRead == false);
            messages = messages.Where(s => s.IsRemoved == false);
            int numberUnreadMessages = messages.Count();

            if (logins.Any())
            {
                var model = new IndexViewModel
                {
                    Username = user.UserName,
                    LastLogin = logins.Last().DateTime,
                    LoginsThisMonth = loginsThisMonth,
                    NumberUnreadMessages = numberUnreadMessages
                };
                return View(model);
            }
            else
            {
                var model = new IndexViewModel
                {
                    Username = user.UserName,
                    LastLogin = DateTime.Now,
                    LoginsThisMonth = 1,
                    NumberUnreadMessages = numberUnreadMessages
                };
                return View(model);
            }             
        }

        public IActionResult About()
        {
            ViewData["Message"] = ".NET Laboration i kursen Distribuerade Informationssystem";
            ViewData["Text"] = "Av Jakob Danielsson & Michael Hjälmö";
            return View();
        }

        public IActionResult Contact()
        {
            ViewData["Message"] = "Your contact page.";

            return View();
        }



        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
